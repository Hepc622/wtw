package com.wtw.website;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class MailUtils {
	private String host = "smtp.qq.com"; // smtp服务器
	private String user = "1211818813"; // 用户名
	private String pwd = "nranhsicridkhhhe"; // 密码
	private String from = "service@hneb.net"; // 发件人地址
	
	private MailUtils(){
		
	}
	
	public static MailUtils getInstance(String host,String user,String pwd,String from){
		MailUtils mu = new MailUtils();
		mu.host = host;
		mu.user = user;
		mu.pwd = pwd;
		mu.from = from;
		
		return mu;
	}

	/**
	 * 发送邮件
	 * 
	 * @param mailTo
	 *            收件人
	 * @param title
	 *            邮件主题
	 * @param content
	 *            邮件内容 换行采用\n
	 * @param attachment
	 *            附件文件，可为null
	 * @throws Exception
	 */
	public void send(String nickNme,String[] mailTo,String [] cs,String []ms, String title, String content,
			File attachment) throws Exception {
		Properties props = new Properties();
		// 设置发送邮件的邮件服务器的属性
		props.put("mail.smtp.host", host);
		// 需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
		props.put("mail.smtp.auth", "true");
		
		
		// 用刚刚设置好的props对象构建一个session
		Session session = Session.getDefaultInstance(props);
		// 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
		// 用（你可以在控制台（console)上看到发送邮件的过程）
		session.setDebug(true);
		// 用session为参数定义消息对象
		MimeMessage message = new MimeMessage(session);
		try {
			
			String nick="";  
	        try {  
	            nick=javax.mail.internet.MimeUtility.encodeText(nickNme);  
	        } catch (UnsupportedEncodingException e) {  
	            e.printStackTrace();  
	        }   
	        message.setFrom(new InternetAddress(nick+" <"+from+">"));  
	        
			// 加载发件人地址
//			message.setFrom(new InternetAddress(from));
			
			if(mailTo==null||mailTo.length==0){
				throw new Exception("收件人不能为空");
			}
			if(title==null||"".equals(title)){
				throw new Exception("主题不能为空");
			}
			if(content==null||"".equals(content)){
				throw new Exception("邮件正文不能为空");
			}
			// 加载收件人地址
			message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(getMailList(mailTo)));
			//抄送 
			if(cs!=null && cs.length>0) 
			{ 
				message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(getMailList(cs))); // 抄送人 
			} 
			if(ms!=null && ms.length>0) 
			{ 
				message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(getMailList(ms))); // 密送人 
			} 
			
			// 加载标题
			message.setSubject(title);
			// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
			Multipart multipart = new MimeMultipart();

			// 设置邮件的文本内容
			BodyPart contentPart = new MimeBodyPart();
			contentPart.setContent(content, "text/html;charset=gbk");
//			contentPart.setText(content);
			multipart.addBodyPart(contentPart);
			if (attachment != null) {
				BodyPart attachmentBodyPart = new MimeBodyPart();
				DataSource source = new FileDataSource(attachment);
				attachmentBodyPart.setDataHandler(new DataHandler(source));

				// 网上流传的解决文件名乱码的方法，其实用MimeUtility.encodeWord就可以很方便的搞定
				// 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
				// sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
				// messageBodyPart.setFileName("=?GBK?B?" +
				// enc.encode(attachment.getName().getBytes()) + "?=");

				// MimeUtility.encodeWord可以避免文件名乱码
				attachmentBodyPart.setFileName(MimeUtility.encodeWord(attachment.getName()));
				multipart.addBodyPart(attachmentBodyPart);
			}

			// 将multipart对象放到message中
			message.setContent(multipart, "text/html;charset=gbk");
			// 保存邮件
			message.saveChanges();
			// 发送邮件
			Transport transport = session.getTransport("smtp");
			// 连接服务器的邮箱
			transport.connect(host, user, pwd);
			// 把邮件发送出去
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	private String getMailList(String[] mailArray) {
		StringBuffer toList = new StringBuffer();
		int length = mailArray.length;
		if (mailArray != null && length < 2) {
			toList.append(mailArray[0]);
		} else {
			for (int i = 0; i < length; i++) {
				toList.append(mailArray[i]);
				if (i != (length - 1)) {
					toList.append(",");
				}

			}
		}
		return toList.toString();

	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String [] mailTo = {"874694517@qq.com"};
		String [] cc =null;
		String [] ms =null;//{"sunnidy2008@163.com","53816565@qq.com","1211818813@qq.com"};
		
	//	String content = new String(FileReaderUtils.read("c:\\mail.txt"),"gbk");
		
		String host = "smtp.qq.com"; // smtp服务器
		String user = "1211818813"; // 用户名
		String pwd = "nranhsicridkhhhe"; // 密码
		String from = "service@hneb.net"; // 发件人地址
		MailUtils mu = MailUtils.getInstance(host, user, pwd, from);
		mu.send("zhangsan",mailTo,cc,ms, "测试邮件aaa", "<img src='http://wx.qlogo.cn/mmopen/5KaQYTQDibbMvUSfX5aS5WPIgCCRN1ia3ibD04MBBpodYKFSxd0dfhSia8gricicRELAHb1FqeoEIyXmA7895dF5z5ZicAx659MwfHd/0?imageView2/1/w/50/h/50' />", null);
	}

}
