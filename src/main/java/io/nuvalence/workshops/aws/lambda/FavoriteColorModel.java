package io.nuvalence.workshops.aws.lambda;

/**
 * Model representing a person's favorite color.
 */
public class FavoriteColorModel {
    private String name;
    private String color;

    public String getName() {
        return this.name;
    }

    public void setName(String inputName) {
        this.name = inputName;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String inputColor) {
        this.color = inputColor;
    }
}