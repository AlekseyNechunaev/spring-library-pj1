package ru.nechunaev.constant;

public final class Paths {

    private Paths() {

    }

    public static final String INDEX = "/";

    public static final String PERSON = "/person";


    public static final String BOOK = "/book";

    public static final String NEW = "/new";

    public static final String ID = "/{id}";

    public static final String EDIT = "/edit" + ID;

    public static final String DELETE = "/delete" + ID;

    public static final String RELEASE = "/release" + ID;

    public static final String APPOINT = "/appoint" + ID;


}
