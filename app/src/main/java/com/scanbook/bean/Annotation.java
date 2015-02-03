package com.scanbook.bean;



public class Annotation {
	//作者
	private String Author;
	//摘要
	private String Abstract;
	//内容
	private String Content;
    //时间
    private String Time;
    //作者头像
    private String AuthorHead;
    //章节
    private String Cheapter;
    //页码
    private int Page;


    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getAuthorHead() {
        return AuthorHead;
    }

    public void setAuthorHead(String authorHead) {
        AuthorHead = authorHead;
    }

    public String getCheapter() {
        return Cheapter;
    }

    public void setCheapter(String cheapter) {
        Cheapter = cheapter;
    }

    public int getPage() {
        return Page;
    }

    public void setPage(int page) {
        Page = page;
    }

    public String getAuthor() {
		return Author;
	}
	public void setAuthor(String author) {
		Author = author;
	}
	public String getAbstract() {
		return Abstract;
	}
	public void setAbstract(String abstract1) {
		Abstract = abstract1;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	
}
