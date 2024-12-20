-- Додавання категорій
INSERT INTO category (name)
VALUES
    ('Електроніка'),
    ('Одяг'),
    ('Книги'),
    ('Їжа'),
    ('Краса'),
    ('Техніка'),
    ('Спортивні товари'),
    ('Аксесуари'),
    ('Меблі'),
    ('Автотовари');

-- Додавання продуктів
INSERT INTO product (name, price, category_id)
VALUES
    ('Ноутбук HP', 1200.00, 1),
    ('Футболка Nike', 29.99, 2),
    ('Java для початківців', 20.00, 3),
    ('Сендвіч-гриль', 45.50, 4),
    ('Крем для обличчя', 15.99, 5),
    ('Смарт-годинник', 199.99, 1),
    ('Бутилка для води', 10.00, 6),
    ('Штани Adidas', 60.00, 2),
    ('Розкладний стілець', 35.00, 7),
    ('Рюкзак', 50.00, 8),
    ('Шкаф для одягу', 150.00, 9),
    ('Автокрісло', 100.00, 10);
