-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Хост: localhost
-- Время создания: Июн 26 2026 г., 00:09
-- Версия сервера: 10.4.28-MariaDB
-- Версия PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `order_processing`
--

-- --------------------------------------------------------

--
-- Структура таблицы `customers`
--

CREATE TABLE `customers` (
  `id` int(10) UNSIGNED NOT NULL,
  `customer_name` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `phone_number` varchar(30) NOT NULL,
  `contact_person` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Дамп данных таблицы `customers`
--

INSERT INTO `customers` (`id`, `customer_name`, `address`, `phone_number`, `contact_person`) VALUES
(1, 'ООО БОНВИЛЬ', 'г. Тверь, б-р Радищева, д. 29', '+7-495-111-22-01', 'Овштейн Зинаида Владимировна'),
(2, 'ООО АМЕЛИЯ', 'г. Тверь, б-р Радищева, д. 28', '+7-495-111-22-02', 'Орлова Елена Сергеевна'),
(3, 'ООО ДОМА У ДРУЗЕЙ', 'г. Тверь, ул. Симеоновская, д. 6', '+7-495-111-22-03', 'Соколова Ирина Павловна'),
(4, 'ООО ПРИЧАЛ', 'г. Тверь, наб. реки Тьмаки, д. 15', '+7-495-111-22-04', 'Волков Дмитрий Андреевич'),
(5, 'ООО СТАРЫЙ БАКУ', 'г. Тверь, ул. Трехсвятская, д. 14', '+7-495-111-22-05', 'Крылова Мария Олеговна'),
(6, 'ООО РИВЬЕРА', 'г. Тверь, наб. Афанасия Никитина, д. 13', '+7-495-111-22-06', 'Лебедев Сергей Игоревич');

-- --------------------------------------------------------

--
-- Структура таблицы `deal_elements`
--

CREATE TABLE `deal_elements` (
  `id` int(10) UNSIGNED NOT NULL,
  `document_id` int(10) UNSIGNED NOT NULL,
  `item_id` int(10) UNSIGNED NOT NULL,
  `delivery_id` int(10) UNSIGNED NOT NULL,
  `amount` int(10) UNSIGNED NOT NULL,
  `delivery_price` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Дамп данных таблицы `deal_elements`
--

INSERT INTO `deal_elements` (`id`, `document_id`, `item_id`, `delivery_id`, `amount`, `delivery_price`) VALUES
(1, 1, 2, 1, 2, 600.00),
(2, 2, 4, 4, 4, 0.00),
(3, 3, 1, 2, 10, 3500.00),
(4, 4, 5, 1, 10, 600.00);

-- --------------------------------------------------------

--
-- Структура таблицы `delivery_methods`
--

CREATE TABLE `delivery_methods` (
  `id` int(10) UNSIGNED NOT NULL,
  `name` varchar(255) NOT NULL,
  `basic_price` decimal(10,2) NOT NULL,
  `delivery_speed` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Дамп данных таблицы `delivery_methods`
--

INSERT INTO `delivery_methods` (`id`, `name`, `basic_price`, `delivery_speed`) VALUES
(1, 'Доставка по Твери', 600.00, '1 рабочий день'),
(2, 'Доставка по Тверской области', 3500.00, '2-3 рабочих дня'),
(3, 'Транспортная компания', 5000.00, '3-7 рабочих дней'),
(4, 'Самовывоз со склада', 0.00, 'В день готовности заказа');

-- --------------------------------------------------------

--
-- Структура таблицы `documents`
--

CREATE TABLE `documents` (
  `id` int(10) UNSIGNED NOT NULL,
  `customer_id` int(10) UNSIGNED NOT NULL,
  `document_number` varchar(100) NOT NULL,
  `purchase_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Дамп данных таблицы `documents`
--

INSERT INTO `documents` (`id`, `customer_id`, `document_number`, `purchase_date`) VALUES
(1, 1, 'ЗК-001/26', '2026-06-10'),
(2, 2, 'ЗК-002/26', '2026-06-12'),
(3, 6, 'ЗК-003/26', '2026-06-13'),
(4, 3, 'ЗК-004/26', '2026-06-11');

-- --------------------------------------------------------

--
-- Структура таблицы `items`
--

CREATE TABLE `items` (
  `id` int(10) UNSIGNED NOT NULL,
  `item_name` varchar(255) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `item_information` text DEFAULT NULL,
  `has_delivery` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Дамп данных таблицы `items`
--

INSERT INTO `items` (`id`, `item_name`, `price`, `item_information`, `has_delivery`) VALUES
(1, 'Конвекционная печь Abat', 168500.00, 'Профессиональная электрическая печь на 6 уровней для кафе, ресторанов и пекарен.', 1),
(2, 'Холодильный шкаф Polair', 104900.00, 'Среднетемпературный холодильный шкаф объёмом 700 литров', 1),
(3, 'Стол производственный СРП-1200', 18900.00, 'Производственный стол из нержавеющей стали размером 1200 на 600 миллиметров.', 1),
(4, 'Кофемашина Nuova', 285000.00, 'Профессиональная двухгруппная кофемашина для кафе и ресторанов.', 1),
(5, 'Индукционная плита ПИН-4', 142000.00, 'Четырёхконфорочная профессиональная индукционная плита.', 1);

-- --------------------------------------------------------

--
-- Структура таблицы `item_delivery`
--

CREATE TABLE `item_delivery` (
  `item_id` int(10) UNSIGNED NOT NULL,
  `delivery_id` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Дамп данных таблицы `item_delivery`
--

INSERT INTO `item_delivery` (`item_id`, `delivery_id`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(2, 1),
(2, 2),
(2, 3),
(2, 4),
(3, 1),
(3, 4),
(4, 1),
(4, 2),
(4, 3),
(4, 4),
(5, 1),
(5, 2),
(5, 4);

-- --------------------------------------------------------

--
-- Структура таблицы `roles`
--

CREATE TABLE `roles` (
  `id` int(10) UNSIGNED NOT NULL,
  `role_name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Дамп данных таблицы `roles`
--

INSERT INTO `roles` (`id`, `role_name`) VALUES
(1, 'ADMIN'),
(3, 'CUSTOMER'),
(2, 'MANAGER');

-- --------------------------------------------------------

--
-- Структура таблицы `users`
--

CREATE TABLE `users` (
  `id` int(10) UNSIGNED NOT NULL,
  `login` varchar(50) NOT NULL,
  `hash_password` varchar(255) NOT NULL,
  `active_role_id` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Дамп данных таблицы `users`
--

INSERT INTO `users` (`id`, `login`, `hash_password`, `active_role_id`) VALUES
(1, 'admin', '$2a$10$IM5ByBiKRQ/C4gErMMqCDuclCS92xmpoPaVyCCjkULR18sVu9ZbJS', 1),
(6, 'Manager1', '$2a$10$IeFmycZbypoIAie2Ppl2geNxvs7TwA/xGzqvk/OXMZEaO5caybgWm', 2),
(8, 'bonnevillecafe', '$2a$10$jJwjRCpj0tN67YyFzAjiFu0haW.yjYFGZG0lUc2caOy3bW8tWP7xW', 3),
(9, 'ooo_prichal', '$2a$10$/dNYWkVFNNKYczj.lXtxo.6cMxLndSr7RhO/VAXowx3KyoJ5l3Seu', 3),
(10, 'Manager2', '$2a$10$ozlzdJwHzKSh9LbmOk7DeORGecaRRe4FKm/H27MP.VWWadeKaOlyW', 2),
(11, 'AmeliaCoffee', '$2a$10$/D4g2.uRm0.8svVN2StXxOBwbg5tBxWMQr7BkoRzvTEHnakEXF5dC', 3),
(12, 'Rest_withFriends', '$2a$10$3jIPZgppxUvDUX0TCflOa.v99WQNPAMHMcnwRP1p9nOZxNgb83eUi', 3);

-- --------------------------------------------------------

--
-- Структура таблицы `user_roles`
--

CREATE TABLE `user_roles` (
  `role_id` int(10) UNSIGNED NOT NULL,
  `user_id` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Дамп данных таблицы `user_roles`
--

INSERT INTO `user_roles` (`role_id`, `user_id`) VALUES
(1, 1),
(2, 6),
(2, 10),
(3, 8),
(3, 9),
(3, 11),
(3, 12);

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `phone_number` (`phone_number`),
  ADD UNIQUE KEY `customer_name` (`customer_name`,`address`);

--
-- Индексы таблицы `deal_elements`
--
ALTER TABLE `deal_elements`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `document_id` (`document_id`,`item_id`),
  ADD KEY `item_id` (`item_id`),
  ADD KEY `delivery_id` (`delivery_id`);

--
-- Индексы таблицы `delivery_methods`
--
ALTER TABLE `delivery_methods`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Индексы таблицы `documents`
--
ALTER TABLE `documents`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `document_number` (`document_number`),
  ADD KEY `customer_id` (`customer_id`);

--
-- Индексы таблицы `items`
--
ALTER TABLE `items`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `item_name` (`item_name`);

--
-- Индексы таблицы `item_delivery`
--
ALTER TABLE `item_delivery`
  ADD PRIMARY KEY (`item_id`,`delivery_id`),
  ADD KEY `delivery_id` (`delivery_id`);

--
-- Индексы таблицы `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `role_name` (`role_name`);

--
-- Индексы таблицы `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `login` (`login`),
  ADD KEY `active_role_id` (`active_role_id`);

--
-- Индексы таблицы `user_roles`
--
ALTER TABLE `user_roles`
  ADD PRIMARY KEY (`role_id`,`user_id`),
  ADD KEY `user_id` (`user_id`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `customers`
--
ALTER TABLE `customers`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT для таблицы `deal_elements`
--
ALTER TABLE `deal_elements`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT для таблицы `delivery_methods`
--
ALTER TABLE `delivery_methods`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT для таблицы `documents`
--
ALTER TABLE `documents`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT для таблицы `items`
--
ALTER TABLE `items`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT для таблицы `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT для таблицы `users`
--
ALTER TABLE `users`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `deal_elements`
--
ALTER TABLE `deal_elements`
  ADD CONSTRAINT `deal_elements_ibfk_1` FOREIGN KEY (`document_id`) REFERENCES `documents` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `deal_elements_ibfk_2` FOREIGN KEY (`item_id`) REFERENCES `items` (`id`),
  ADD CONSTRAINT `deal_elements_ibfk_3` FOREIGN KEY (`delivery_id`) REFERENCES `delivery_methods` (`id`);

--
-- Ограничения внешнего ключа таблицы `documents`
--
ALTER TABLE `documents`
  ADD CONSTRAINT `documents_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`) ON DELETE CASCADE;

--
-- Ограничения внешнего ключа таблицы `item_delivery`
--
ALTER TABLE `item_delivery`
  ADD CONSTRAINT `item_delivery_ibfk_1` FOREIGN KEY (`item_id`) REFERENCES `items` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `item_delivery_ibfk_2` FOREIGN KEY (`delivery_id`) REFERENCES `delivery_methods` (`id`) ON DELETE CASCADE;

--
-- Ограничения внешнего ключа таблицы `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`active_role_id`) REFERENCES `roles` (`id`);

--
-- Ограничения внешнего ключа таблицы `user_roles`
--
ALTER TABLE `user_roles`
  ADD CONSTRAINT `user_roles_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  ADD CONSTRAINT `user_roles_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
