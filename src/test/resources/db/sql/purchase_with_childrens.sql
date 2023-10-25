INSERT INTO
    PURCHASE
    (
        PRICE,
        QUANTITY
    )
VALUES
    (20.50, 1);

INSERT INTO
    TICKET
    (
        NUMBER,
        ID_ROOM,
        ID_CHAIR,
        TICKET_DAY,
        SESSION,
        PRICE,
        CREATE_AT,
        ID_PURCHASE
    )
VALUES
    ('202222301', 1, 1, NOW(), '22:30', 22.30, NOW(), 1 );