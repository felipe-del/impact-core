package com.impact.core.module.productStatus.enun;


/**
 * Enum representing the possible statuses of a product in the system.
 * <p>
 * This enum is used to categorize the state of a product, such as whether it is available,
 * on loan, or under a specific status like "Earring".
 * </p>
 */
public enum EProductStatus {
    PRODUCT_STATUS_AVAILABLE,
    PRODUCT_STATUS_EARRING,
    PRODUCT_STATUS_LOANED;

    /**
     * Returns the name of the status without the "PRODUCT_STATUS_" prefix.
     * <p>
     * This method is used to provide a more user-friendly string representation of the status.
     * </p>
     *
     * @return the name of the product status without the prefix.
     */
    @Override
    public String toString() {
        return name().replace("PRODUCT_STATUS_", "");
    }
}
