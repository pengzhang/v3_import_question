package com.ctb.entity;


/**
 * 主观题
 * @author zp
 *
 */
public class SubjectiveItem extends IdEntity {
	private String title;
	// 题干
	private String stem;
	private String stemPicture;
	private String option;
	private String optionPicture;
	// 解析
	private String analyze;
	private String analyzePicture;
	// 知识点和产生式的code
	private String knowledgeCode;
	private String knowledges;
	private String productionCode;
	private String productions;
	private String answer;
	private int star;
	private String comment;
	private String remark;
	private String audio;
	private String video;
	// 出处
	private String source;
	private boolean isDel = false;
	private boolean isCheck = false;
	private int isCompleted;
	private int type;
	private String grade;
	private String subject;
	private String stage;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStem() {
		return stem;
	}

	public void setStem(String stem) {
		this.stem = stem;
	}

	public String getStemPicture() {
		return stemPicture;
	}

	public void setStemPicture(String stemPicture) {
		this.stemPicture = stemPicture;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getOptionPicture() {
		return optionPicture;
	}

	public void setOptionPicture(String optionPicture) {
		this.optionPicture = optionPicture;
	}

	public String getAnalyze() {
		return analyze;
	}

	public void setAnalyze(String analyze) {
		this.analyze = analyze;
	}

	public String getAnalyzePicture() {
		return analyzePicture;
	}

	public void setAnalyzePicture(String analyzePicture) {
		this.analyzePicture = analyzePicture;
	}
	
	public String getKnowledgeCode() {
		return knowledgeCode;
	}

	public void setKnowledgeCode(String knowledgeCode) {
		this.knowledgeCode = knowledgeCode;
	}
	
	public String getProductionCode() {
		return productionCode;
	}

	public void setProductionCode(String productionCode) {
		this.productionCode = productionCode;
	}

	public String getKnowledges() {
		return knowledges;
	}

	public void setKnowledges(String knowledges) {
		this.knowledges = knowledges;
	}

	public String getProductions() {
		return productions;
	}

	public void setProductions(String productions) {
		this.productions = productions;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAudio() {
		return audio;
	}

	public void setAudio(String audio) {
		this.audio = audio;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public boolean getIsDel() {
		return isDel;
	}

	public void setIsDel(boolean isDel) {
		this.isDel = isDel;
	}

	public boolean getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

	public int getIsCompleted() {
		return isCompleted;
	}

	public void setIsCompleted(int isCompleted) {
		this.isCompleted = isCompleted;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

}