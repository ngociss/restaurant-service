-- ======================================================
-- Schema: restaurant_service
-- Mục đích: Lưu thông tin nhà hàng, sản phẩm và phê duyệt đơn
-- Saga Pattern (choreography) demo - KHÔNG gồm Outbox
-- ======================================================

-- 1️⃣ Tạo schema riêng cho service
CREATE SCHEMA IF NOT EXISTS restaurant_service;

-- 2️⃣ Enum trạng thái phê duyệt đơn
CREATE TYPE restaurant_service.approval_status AS ENUM ('PENDING', 'APPROVED', 'REJECTED');

-- 3️⃣ Bảng nhà hàng (Restaurant master)
CREATE TABLE restaurant_service.restaurants
(
    id         UUID PRIMARY KEY,
    name       TEXT        NOT NULL,
    is_active  BOOLEAN     NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- 4️⃣ Bảng sản phẩm của nhà hàng (menu items)
CREATE TABLE restaurant_service.restaurant_products
(
    id            UUID PRIMARY KEY,
    restaurant_id UUID           NOT NULL REFERENCES restaurant_service.restaurants (id) ON DELETE CASCADE,
    product_id    UUID           NOT NULL,
    name          TEXT           NOT NULL,
    available     BOOLEAN        NOT NULL DEFAULT TRUE,
    price         NUMERIC(10, 2) NOT NULL CHECK (price >= 0)
);

-- 5️⃣ Bảng lưu quyết định phê duyệt đơn hàng
CREATE TABLE restaurant_service.restaurant_approvals
(
    id               UUID PRIMARY KEY,
    restaurant_id    UUID                               NOT NULL REFERENCES restaurant_service.restaurants (id),
    order_id         UUID                               NOT NULL,
    status           restaurant_service.approval_status NOT NULL DEFAULT 'PENDING',
    failure_messages TEXT[],
    created_at       TIMESTAMPTZ                        NOT NULL DEFAULT NOW()
);

-- 6️⃣ Tạo index hỗ trợ truy vấn
CREATE INDEX IF NOT EXISTS idx_restaurant_approvals_order_id
    ON restaurant_service.restaurant_approvals(order_id);

CREATE INDEX IF NOT EXISTS idx_restaurant_products_restaurant_id
    ON restaurant_service.restaurant_products(restaurant_id);

-- 7️⃣ Dữ liệu mẫu (seed)
INSERT INTO restaurant_service.restaurants (id, name, is_active)
VALUES ('11111111-1111-1111-1111-111111111111', 'Gà Rán Một Mình', TRUE),
       ('22222222-2222-2222-2222-222222222222', 'Bún Chả Đêm Khuya', TRUE) ON CONFLICT DO NOTHING;

INSERT INTO restaurant_service.restaurant_products (id, restaurant_id, product_id, name, available, price)
VALUES ('33333333-3333-3333-3333-333333333333', '11111111-1111-1111-1111-111111111111',
        'a1a1a1a1-a1a1-a1a1-a1a1-a1a1a1a1a1a1', 'Gà giòn cay', TRUE, 59000),
       ('44444444-4444-4444-4444-444444444444', '11111111-1111-1111-1111-111111111111',
        'b2b2b2b2-b2b2-b2b2-b2b2-b2b2b2b2b2b2', 'Gà không cay', TRUE, 56000),
       ('55555555-5555-5555-5555-555555555555', '22222222-2222-2222-2222-222222222222',
        'c3c3c3c3-c3c3-c3c3-c3c3-c3c3c3c3c3c3', 'Bún chả Hà Nội', TRUE, 45000) ON CONFLICT DO NOTHING;




