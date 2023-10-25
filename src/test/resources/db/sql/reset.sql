
-- Desativar temporariamente as verificações de chave estrangeira
SET REFERENTIAL_INTEGRITY FALSE;

-- Exclua todos os registros da tabela PURCHASE
DELETE FROM PURCHASE;

-- Exclua todos os registros da tabela TICKET

DELETE FROM TICKET;

-- Reinicie o valor do ID auto-incremento da tabela PURCHASE
ALTER TABLE PURCHASE ALTER COLUMN ID RESTART WITH 1;

-- Reinicie o valor do ID auto-incremento da tabela TICKET
ALTER TABLE TICKET ALTER COLUMN ID RESTART WITH 1;

-- Reativar as verificações de chave estrangeira
SET REFERENTIAL_INTEGRITY TRUE;
