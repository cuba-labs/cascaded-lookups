-- begin DEMO_COUNTRY
create table DEMO_COUNTRY (
    ID varchar(36) not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    VERSION integer,
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255),
    --
    primary key (ID)
)^
-- end DEMO_COUNTRY
-- begin DEMO_CITY
create table DEMO_CITY (
    ID varchar(36) not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    VERSION integer,
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255),
    COUNTRY_ID varchar(36),
    --
    primary key (ID)
)^
-- end DEMO_CITY
-- begin DEMO_ADDRESS
create table DEMO_ADDRESS (
    ID varchar(36) not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    VERSION integer,
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    COUNTRY_ID varchar(36),
    CITY_ID varchar(36),
    ADDRESS_LINE varchar(255),
    --
    primary key (ID)
)^
-- end DEMO_ADDRESS
