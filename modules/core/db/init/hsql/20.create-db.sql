-- begin DEMO_CITY
alter table DEMO_CITY add constraint FK_DEMO_CITY_COUNTRY_ID foreign key (COUNTRY_ID) references DEMO_COUNTRY(ID)^
create index IDX_DEMO_CITY_COUNTRY on DEMO_CITY (COUNTRY_ID)^
-- end DEMO_CITY
-- begin DEMO_ADDRESS
alter table DEMO_ADDRESS add constraint FK_DEMO_ADDRESS_COUNTRY_ID foreign key (COUNTRY_ID) references DEMO_COUNTRY(ID)^
alter table DEMO_ADDRESS add constraint FK_DEMO_ADDRESS_CITY_ID foreign key (CITY_ID) references DEMO_CITY(ID)^
create index IDX_DEMO_ADDRESS_COUNTRY on DEMO_ADDRESS (COUNTRY_ID)^
create index IDX_DEMO_ADDRESS_CITY on DEMO_ADDRESS (CITY_ID)^
-- end DEMO_ADDRESS
