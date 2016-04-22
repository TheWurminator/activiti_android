package rendezvous.activiti;

/**
 * Created by amona_000 on 4/21/2016.
 */
public class ActivitiListModel {
    String postOwnerName, destination;
    DateTime dateStart, dateEnd;
    int cost, maxAttendees;
    double lattitude, longitude;

    public ActivitiListModel (String postOwnername, String destination, DateTime start, DateTime end, int cost,
                              int maxAttendees, double longitude, double lattitude){
        this.cost = cost;
        this.dateStart = start;
        this.dateEnd = end;
        this.postOwnerName = postOwnername;
        this.destination = destination;
        this.maxAttendees = maxAttendees;
        this.longitude = longitude;
        this.lattitude = lattitude;
    }
}
