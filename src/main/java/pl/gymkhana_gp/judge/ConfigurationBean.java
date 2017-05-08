package pl.gymkhana_gp.judge;

import org.springframework.stereotype.Component;

@Component
public class ConfigurationBean {
	 private String xmlFilePath = "JudgeGG-test.xml";

	public String getXmlFilePath() {
		return xmlFilePath;
	}

	public String getXmlBackupFilePath() {
		return getXmlFilePath() + ".backup";
	}

	public void setXmlFilePath(String xmlFilePath) {
		this.xmlFilePath = xmlFilePath;
	}
}
