

SET REFERENTIAL_INTEGRITY FALSE;


DELETE FROM PURCHASE;



DELETE FROM TICKET;


ALTER TABLE PURCHASE ALTER COLUMN ID RESTART WITH 1;


ALTER TABLE TICKET ALTER COLUMN ID RESTART WITH 1;


SET REFERENTIAL_INTEGRITY TRUE;
