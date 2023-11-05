CREATE TABLE `product_group_list` (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at TIMESTAMP(6),
    `product_group_id` bigint UNSIGNED DEFAULT NULL,
    `product_id` bigint UNSIGNED DEFAULT NULL,
    KEY `FK9mgjw7mdocnytoi2a45jo4vgc` (`product_id`),
    KEY `FKgox76pyv0kmrobtr95xyngamo` (`product_group_id`),
    CONSTRAINT `FK9mgjw7mdocnytoi2a45jo4vgc` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
    CONSTRAINT `FKgox76pyv0kmrobtr95xyngamo` FOREIGN KEY (`product_group_id`) REFERENCES `product_group` (`id`),
    UNIQUE KEY `product_product_group_uk` (`product_id`,`product_group_id`)
);