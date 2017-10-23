package fr.jenniault.projet_seismes;

public class Seisme
{
    private String id;
    private String title;
    private String update;
    private String link;
    private String sumary;
    private String point;
    private String elev;
    private String age;
    private String magnitude;
    private String contributor;
    private String author;

    public Seisme(String id, String title, String update, String link, String  sumary, String point, String elev, String age, String magnitude, String contributor, String author)
    {
        this.id = id;
        this.title = title;
        this.update = update;
        this.link = link;
        this.sumary = sumary;
        this.point = point;
        this.elev = elev;
        this.age = age;
        this.magnitude = magnitude;
        this.contributor = contributor;
        this.author = author;
    }

    public Seisme()
    {

    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getUpdate()
    {
        return update;
    }

    public void setUpdate(String update)
    {
        this.update = update;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }

    public String getSumary()
    {
        return sumary;
    }

    public void setSumary(String sumary)
    {
        this.sumary = sumary;
    }

    public String getPoint()
    {
        return point;
    }

    public void setPoint(String point)
    {
        this.point = point;
    }

    public String getElev() {
        return elev;
    }

    public void setElev(String elev)
    {
        this.elev = elev;
    }

    public String getAge()
    {
        return age;
    }

    public void setAge(String age)
    {
        this.age = age;
    }

    public String getMagnitude()
    {
        return magnitude;
    }

    public void setMagnitude(String magnitude)
    {
        this.magnitude = magnitude;
    }

    public String getContributor()
    {
        return contributor;
    }

    public void setContributor(String contributor)
    {
        this.contributor = contributor;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }
}
