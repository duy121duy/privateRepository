public class DriverLocation {
    private long driverId;
    private long longitude;
    private long latitude;

    public DriverLocation() {
    }

    public DriverLocation(long driverId, long longitude, long latitude) {
        this.driverId = driverId;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public long getDriverId() {
        return driverId;
    }

    public void setDriverId(long driverId) {
        this.driverId = driverId;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }
}
