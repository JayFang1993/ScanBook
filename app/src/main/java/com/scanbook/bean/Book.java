package com.scanbook.bean;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
	//图书ID
	private String id;
    //图书标题
	private String Title;	
	//图书作者
    private String Author;
    //作者信息
    private String AuthorInfo;
    //图书出版社
    private String Publisher;
    //出版时间
    private String PublishDate;
    //图书ISBN码
    private String ISBN;
    //图书价格
    private String Price;
    //图书页数
    private String Page;
    //图书评分
    private double Rate;
    //图书标签
    private String Tag;
    //图书目录
    private String Content;
    //图书摘要
    private String Summary;
    //图书图片
    private String Bitmap;
    //图书评论数量
    private int ReviewCount;

    private String Url;

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getAuthor() {
		return Author;
	}
	public void setAuthor(String author) {
		Author = author;
	}
	public String getAuthorInfo() {
		return AuthorInfo;
	}
	public void setAuthorInfo(String authorInfo) {
		AuthorInfo = authorInfo;
	}
	public String getPublisher() {
		return Publisher;
	}
	public void setPublisher(String publisher) {
		Publisher = publisher;
	}
	public String getPublishDate() {
		return PublishDate;
	}
	public void setPublishDate(String publishDate) {
		PublishDate = publishDate;
	}
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
	public String getPrice() {
		return Price;
	}
	public void setPrice(String price) {
		Price = price;
	}
	public String getPage() {
		return Page;
	}
	public void setPage(String page) {
		Page = page;
	}

	public String getTag() {
		return Tag;
	}
	public void setTag(String tag) {
		Tag = tag;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getSummary() {
		return Summary;
	}
	public void setSummary(String summary) {
		Summary = summary;
	}

    public String getBitmap() {
        return Bitmap;
    }

    public void setBitmap(String bitmap) {
        Bitmap = bitmap;
    }

    public double getRate() {
        return Rate;
    }

    public void setRate(double rate) {
        Rate = rate;
    }

    public int getReviewCount() {
        return ReviewCount;
    }

    public void setReviewCount(int reviewCount) {
        ReviewCount = reviewCount;
    }

    public static Creator<Book> getCREATOR() {
        return CREATOR;
    }

    public static void setCREATOR(Creator<Book> CREATOR) {
        Book.CREATOR = CREATOR;
    }

    /*
             * 实现Parcelable接口的方法
             * 1.getCreator()
             * 2.setCreator()
             * 3.Parcelable.Creator() 构造方法
             * 4.describeContents()
             * 5.writeToParcel()
             */
	public static Creator<Book> getCreator() {
		return CREATOR;
	}
	
	public static void setCreator(Creator<Book> creator) {
		CREATOR = creator;
	}
	
	public static Creator<Book> CREATOR = new Creator<Book>() {
        public Book createFromParcel(Parcel source) {
            Book bookInfo = new Book();
            bookInfo.Title = source.readString();
            bookInfo.Bitmap = source.readString();
            bookInfo.Author = source.readString();
            bookInfo.Publisher = source.readString();
            bookInfo.PublishDate = source.readString();
            bookInfo.ISBN = source.readString();
            bookInfo.Summary = source.readString();
            bookInfo.id=source.readString();;
            bookInfo.AuthorInfo=source.readString();
            bookInfo.Page=source.readString();
            bookInfo.Price=source.readString();;
            bookInfo.Rate=source.readDouble();
            bookInfo.Tag=source.readString();
            bookInfo.Content=source.readString();
            bookInfo.ReviewCount=source.readInt();
            bookInfo.Url=source.readString();
            return bookInfo;
        }
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
    
    public int describeContents() {
        return 0;
    }
    
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Title);
        dest.writeString(Bitmap);
        dest.writeString(Author);
        dest.writeString(Publisher);
        dest.writeString(PublishDate);
        dest.writeString(ISBN);
        dest.writeString(Summary);
        dest.writeString(id);
        dest.writeString(AuthorInfo);
        dest.writeString(Page);
        dest.writeString(Price);
        dest.writeDouble(Rate);
        dest.writeString(Tag);
        dest.writeString(Content);
        dest.writeInt(ReviewCount);
        dest.writeString(Url);
    }

}
