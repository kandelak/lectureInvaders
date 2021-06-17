package main.Controller;

import java.util.Locale;
import java.util.Objects;

/**
 * Represents a dimension with a width and height.
 */
public record Dimension2D(double width, double height) {

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(height, width);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Dimension2D other)) {
            return false;
        }
        return height == other.height && width == other.width;
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "Dimension2D [width=%.2f, height=%.2f]", width, height);
    }


    public Dimension2D scale(double factor) {
        return new Dimension2D(width * factor, height * factor);
    }

    public Point2D toPoint() {
        return new Point2D(width, height);
    }

}
