package com.cognizant.util;

public class HAEmailUtility {
	
	private String[] sendersList;
    
    private String mailSubject="[No Subject]";
    
	private String[] bccList;
	
	public String[] getSendersList() {
		return sendersList;
	}

	public void setSendersList(String[] sendersList) {
		this.sendersList = sendersList;
	}

	public String getMailSubject() {
		return mailSubject;
	}

	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	public String[] getBccList() {
		return bccList;
	}

	public void setBccList(String[] bccList) {
		this.bccList = bccList;
	}
	
	public String createMailForJenkinsFailure(String hostName, String ip, String date, String time, String url) {
		StringBuilder message = new StringBuilder();
		
		message.append("<html>");
		message.append("<header>");
		message.append("</header>");
		message.append("Hi,<br/>");
		message.append("<body>");
		message.append("<p>Jenkins has stopped due to certain issues. Backup jenkins is coming up in a few seconds.");
		message.append("<br/>");
		message.append("You might experience a short period of incativity in jenkins due to this. Please do not panic.</p>");
		message.append("Jenkins failed node details:");
		message.append("<br/>");
		message.append("<p>");
		message.append("<table border=1px cellPadding=4px width=50%>");
		message.append("<tr bgcolor=#7799cc>");
		message.append("<th>Host Name</th>");
		message.append("<th>IP Address</th>");
		message.append("<th>Date</th>");
		message.append("<th>Time</th>");
		message.append("</tr>");
		message.append("<tr>");
		message.append("<td>").append(hostName).append("</td>");
		message.append("<td>").append(ip).append("</td>");
		message.append("<td>").append(date).append("</td>");
		message.append("<td>").append(time).append("</td>");
		message.append("</tr>");
		message.append("</table>");
		message.append("<br/>");
		message.append("</p>");
		message.append("Click here to continue : ").append(url);
		message.append("<br/>");
		message.append("<br/>");
		message.append("Contact jpaashelp@cognizant.com for any queries or assistance.");
		message.append("<br/>");
		message.append("<p>");
		message.append("Regards,<br/>");
		message.append("CloudSet Support Team.");
		message.append("</p>");
		message.append("</body>");
		message.append("</html>");
		
		return message.toString();
	}

}
