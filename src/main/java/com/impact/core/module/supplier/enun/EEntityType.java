package com.impact.core.module.supplier.enun;

/**
 * Enumeration representing the types of entities in the system.
 * <p>
 * The {@code EEntityType} enumeration is used to distinguish between physical and legal entity types.
 */
public enum EEntityType {
    TYPE_PHYSICAL,
    TYPE_LEGAL;

    /**
     * Returns the string representation of the entity type without the {@code "TYPE_"} prefix.
     *
     * @return a formatted {@link String} representation of the entity type
     */
    @Override
    public String toString() {
        return this.name().replaceFirst("TYPE_", "");
    }
}
