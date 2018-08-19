package refactor;

public class Rental {

    Movie movie;
    private int daysRented;

    public Rental(Movie movie, int daysRented) {
        this.movie = movie;
        this.daysRented = daysRented;
    }

    public int getDaysRented() {
        return daysRented;
    }

    public Movie getMovie() {
        return movie;
    }

    int getFrequentRenterPoints() {
        return movie.price.getFrequentRenterPoints(daysRented);
    }

    double getCharge() {
        return movie.price.getCharge(daysRented);
    }
}
