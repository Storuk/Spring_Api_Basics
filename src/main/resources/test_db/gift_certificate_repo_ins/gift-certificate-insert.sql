INSERT INTO gift_certificate
VALUES (1, 'mac', 'ice', 3, 3, '2022-01-07T22:00Z', '2022-01-07T22:00Z'),
       (2, 'kfc', 'nuggets', 4, 7, '2022-01-07T22:00Z','2022-01-07T22:00Z');

INSERT INTO tag
VALUES (1, 'cheap'), (2, 'expensive'), (3, 'old');

INSERT INTO gift_certificate_tag
VALUES (1, 1, 1), (2, 1, 2), (3, 1, 3);
