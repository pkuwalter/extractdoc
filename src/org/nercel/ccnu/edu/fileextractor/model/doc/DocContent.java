package org.nercel.ccnu.edu.fileextractor.model.doc;

import org.nercel.ccnu.edu.fileextractor.model.Content;

public class DocContent extends Content{
	
	private String questionId;//试题ID
	private String questionType;//题型
	private String questionDBYear;//题库年度
	private String questionDBType;//题库类型
	private String applicableClassId;//适用年级
	private String skill;//能力指标
	private String estimatedTime;//预估解题时间
	private String diffForQuestion;//困难度
	private String identify;//鉴别度
	private String guess;//猜度
	private int isOfenTest;//常考题
	private String modelType;//版型
	private String questionStem;//题目文字
	private String imagePath;//题目图片路径
	private String rightAnswer;//答案
	private String explainDocUri;//详解DOC存放地址
	private String explainImgUri;//详解DOC的截图地址
	private String printer;//打印版路径
	private String note;//试题备注
	private String author;//出题者
	private int isDiagnosis;//是否为诊断题
	private String parentQuestionId;//父试题
	private String mediaId;//多媒体讲解资源ID号
	private String WordUri;//试题word路径
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public String getQuestionType() {
		return questionType;
	}
	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}
	public String getQuestionDBYear() {
		return questionDBYear;
	}
	public void setQuestionDBYear(String questionDBYear) {
		this.questionDBYear = questionDBYear;
	}
	public String getQuestionDBType() {
		return questionDBType;
	}
	public void setQuestionDBType(String questionDBType) {
		this.questionDBType = questionDBType;
	}
	public String getApplicableClassId() {
		return applicableClassId;
	}
	public void setApplicableClassId(String applicableClassId) {
		this.applicableClassId = applicableClassId;
	}
	public String getSkill() {
		return skill;
	}
	public void setSkill(String skill) {
		this.skill = skill;
	}
	public String getEstimatedTime() {
		return estimatedTime;
	}
	public void setEstimatedTime(String estimatedTime) {
		this.estimatedTime = estimatedTime;
	}
	public String getDiffForQuestion() {
		return diffForQuestion;
	}
	public void setDiffForQuestion(String diffForQuestion) {
		this.diffForQuestion = diffForQuestion;
	}
	public String getIdentify() {
		return identify;
	}
	public void setIdentify(String identify) {
		this.identify = identify;
	}
	public String getGuess() {
		return guess;
	}
	public void setGuess(String guess) {
		this.guess = guess;
	}
	public int getIsOfenTest() {
		return isOfenTest;
	}
	public void setIsOfenTest(int isOfenTest) {
		this.isOfenTest = isOfenTest;
	}
	public String getModelType() {
		return modelType;
	}
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}
	public String getQuestionStem() {
		return questionStem;
	}
	public void setQuestionStem(String questionStem) {
		this.questionStem = questionStem;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getRightAnswer() {
		return rightAnswer;
	}
	public void setRightAnswer(String rightAnswer) {
		this.rightAnswer = rightAnswer;
	}
	public String getExplainDocUri() {
		return explainDocUri;
	}
	public void setExplainDocUri(String explainDocUri) {
		this.explainDocUri = explainDocUri;
	}
	public String getExplainImgUri() {
		return explainImgUri;
	}
	public void setExplainImgUri(String explainImgUri) {
		this.explainImgUri = explainImgUri;
	}
	public String getPrinter() {
		return printer;
	}
	public void setPrinter(String printer) {
		this.printer = printer;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getIsDiagnosis() {
		return isDiagnosis;
	}
	public void setIsDiagnosis(int isDiagnosis) {
		this.isDiagnosis = isDiagnosis;
	}
	public String getParentQuestionId() {
		return parentQuestionId;
	}
	public void setParentQuestionId(String parentQuestionId) {
		this.parentQuestionId = parentQuestionId;
	}
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public String getWordUri() {
		return WordUri;
	}
	public void setWordUri(String wordUri) {
		WordUri = wordUri;
	}
}
