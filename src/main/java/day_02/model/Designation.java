
package day_02.model;

import java.util.Objects;

public final class Designation {

    private final int designationId;
    private final String designationName;

    public Designation(int designationId, String designationName) {
        if (designationId <= 0) {
            throw new IllegalArgumentException("Invalid designation ID");
        }

        if (designationName == null || designationName.isBlank()) {
            throw new IllegalArgumentException("Designation name cannot be empty");
        }

        this.designationId = designationId;
        this.designationName = designationName;
    }

    public int getDesignationId() {
        return designationId;
    }

    public String getDesignationName() {
        return designationName;
    }

    @Override
    public String toString() {
        return "Designation{" +
                "designationId=" + designationId +
                ", designationName='" + designationName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Designation)) {
            return false;
        }

        Designation other = (Designation) obj;

        return designationId == other.designationId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(designationId);
    }
}
