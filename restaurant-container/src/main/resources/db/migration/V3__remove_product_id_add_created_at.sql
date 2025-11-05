-- ======================================================
-- Migration V3: Xóa product_id thừa, thêm created_at
-- ======================================================

-- Xóa cột product_id vì bảng này chính là master data của products
ALTER TABLE restaurant_service.restaurant_products
DROP COLUMN IF EXISTS product_id;

-- Thêm cột created_at để tracking
ALTER TABLE restaurant_service.restaurant_products
ADD COLUMN IF NOT EXISTS created_at TIMESTAMPTZ NOT NULL DEFAULT NOW();

-- Cập nhật dữ liệu cũ nếu đã có
UPDATE restaurant_service.restaurant_products
SET created_at = NOW()
WHERE created_at IS NULL;

