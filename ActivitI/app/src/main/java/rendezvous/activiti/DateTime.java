package rendezvous.activiti;

/**
 * Created by amona_000 on 4/21/2016.
 */
public class DateTime {

    //Zero based month
    static String[] monthName =
            {"January", "February", "March", "April", "May",
             "June", "July", "August", "September", "October",
             "November", "December"};

    //DateTime object. Holds the date and the time.
    private int day, month, year;
    private int hour, minute;

    //Overloaded constructor, one takes just month, day, year
    public DateTime(int day, int month, int year){
        this.day = day;
        this.month = month;
        this.year = year;
    }

    //Other constructor also takes hour and minute
    public DateTime(int day, int month, int year, int hour, int minute){
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
    }

    //returns the string for the server request
    public String getRequestFormat() {
        return year+"-"+((month < 10)?"0"+month:month)+"-"+((day<10)?"0"+day:day)+
                " "+((hour<10)?"0"+hour:hour)+"-"+((minute<10)?"0"+minute:minute)+"-00";
    }

    //Formats the string to a form that is compatible with the server
    public String formatedDateTime() {
        return monthName[month%12] + " " + day +", "+year +" at "+hour+":"+minute;
    }

    public void setDay(int day){ this.day = day;}
    public void setMonth(int month) {this.month = month;}
    public void setYear(int year) {this.year = year;}
    public void setHour(int hour) {this.hour = hour;}
    public void setMinute(int minute) {this.minute = minute;}

    public int getDay() {return this.day; }
    public int getMonth() {return this.month;}
    public String getMonthName() {return monthName[this.month];}
    public int getYear() {return this.year;}
    public int getHour() {return this.hour;}
    public int getMinute() {return this.minute;}
}
