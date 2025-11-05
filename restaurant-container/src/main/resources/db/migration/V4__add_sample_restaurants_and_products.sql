-- ======================================================
-- Migration V4: Add sample data for restaurants and products
-- Purpose: Provide test data with 4 restaurants and 10 products
-- ======================================================

-- 1️⃣ Insert 4 sample restaurants
INSERT INTO restaurant_service.restaurants (id, name, is_active, created_at)
VALUES
    ('33333333-3333-3333-3333-333333333333', 'Phở Bò Truyền Thống', TRUE, NOW()),
    ('44444444-4444-4444-4444-444444444444', 'Lẩu Thái Tomyum', TRUE, NOW())
ON CONFLICT (id) DO NOTHING;

-- 2️⃣ Insert 10 sample products distributed across restaurants
INSERT INTO restaurant_service.restaurant_products (id, restaurant_id, name, available, price, stock, created_at)
VALUES
    -- Products for 'Gà Rán Một Mình' (already exists from V1)
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '11111111-1111-1111-1111-111111111111', 'Gà rán truyền thống', TRUE, 55000, 150, NOW()),
    ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '11111111-1111-1111-1111-111111111111', 'Khoai tây chiên', TRUE, 25000, 200, NOW()),

    -- Products for 'Bún Chả Đêm Khuya' (already exists from V1)
    ('cccccccc-cccc-cccc-cccc-cccccccccccc', '22222222-2222-2222-2222-222222222222', 'Nem rán Hà Nội', TRUE, 30000, 120, NOW()),
    ('dddddddd-dddd-dddd-dddd-dddddddddddd', '22222222-2222-2222-2222-222222222222', 'Chả giò', TRUE, 35000, 100, NOW()),

    -- Products for 'Phở Bò Truyền Thống'
    ('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', '33333333-3333-3333-3333-333333333333', 'Phở bò tái', TRUE, 50000, 80, NOW()),
    ('ffffffff-ffff-ffff-ffff-ffffffffffff', '33333333-3333-3333-3333-333333333333', 'Phở bò chín', TRUE, 55000, 75, NOW()),
    ('10101010-1010-1010-1010-101010101010', '33333333-3333-3333-3333-333333333333', 'Phở gà', TRUE, 45000, 90, NOW()),

    -- Products for 'Lẩu Thái Tomyum'
    ('20202020-2020-2020-2020-202020202020', '44444444-4444-4444-4444-444444444444', 'Lẩu Thái hải sản', TRUE, 250000, 40, NOW()),
    ('30303030-3030-3030-3030-303030303030', '44444444-4444-4444-4444-444444444444', 'Lẩu Thái gà', TRUE, 180000, 50, NOW()),
    ('40404040-4040-4040-4040-404040404040', '44444444-4444-4444-4444-444444444444', 'Mì trứng thêm', TRUE, 15000, 200, NOW())
ON CONFLICT (id) DO NOTHING;

-- 3️⃣ Add comments for documentation
COMMENT ON TABLE restaurant_service.restaurants IS
    'Master table for restaurants. Now contains 4 sample restaurants for testing.';

COMMENT ON TABLE restaurant_service.restaurant_products IS
    'Products/menu items for each restaurant. Now contains 10+ sample products across all restaurants.';

