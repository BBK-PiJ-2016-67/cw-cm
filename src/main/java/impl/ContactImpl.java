package impl;

import spec.Contact;

/**
 * ContactImpl is a class implementing {@see Contact}.
 *
 * @author BBK-PiJ-2016-67
 */
public final class ContactImpl implements Contact {
  private final int id;
  private final String name;
  private String notes = "";

  /**
   * Initialises the ContactImpl class.
   *
   * @param id                           the id of the contact
   * @param name                         the name of the contact
   * @throws IllegalArgumentException    if the id is not greater
   *                                     than zero
   * @throws NullPointerException        if the name is null
   */
  public ContactImpl(int id, String name) {
    this(id, name, "");
  }

  /**
   * Initialises the ContactImpl class.
   *
   * @param id                           the id of the contact
   * @param name                         the name of the contact
   * @param notes                        initial notes about the
   *                                     contact
   * @throws IllegalArgumentException    if the id is not greater
   *                                     than zero
   * @throws NullPointerException        if the name or the notes
   *                                     are null
   */
  public ContactImpl(int id, String name, String notes) {
    if (id <= 0) {
      throw new IllegalArgumentException("id must be greater than zero");
    } else if (name == null) {
      throw new NullPointerException("name cannot be null");
    } else if (notes == null) {
      throw new NullPointerException("notes cannot be null");
    }
    this.id = id;
    this.name = name;
    this.notes = notes;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public int getId() {
    return this.id;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public String getName() {
    return this.name;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public String getNotes() {
    return this.notes;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public void addNotes(String note) {
    if (note == null) {
      throw new NullPointerException("note cannot be null");
    }
    if (!this.notes.equals("")) {
      this.notes += ", ";
    }
    this.notes += note;
  }
}
