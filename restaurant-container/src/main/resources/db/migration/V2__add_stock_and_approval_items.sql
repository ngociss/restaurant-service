-- ======================================================
-- Migration V2: Add stock management and approval items
-- Purpose: Enable quantity validation and inventory tracking
-- ======================================================

-- 1️⃣ Add stock column to restaurant_products
ALTER TABLE restaurant_service.restaurant_products
ADD COLUMN stock INTEGER NOT NULL DEFAULT 999;

-- 2️⃣ Update existing seed data with reasonable stock values
UPDATE restaurant_service.restaurant_products
SET stock = 100
WHERE id IN (
    '33333333-3333-3333-3333-333333333333',
    '44444444-4444-4444-4444-444444444444',
    '55555555-5555-5555-5555-555555555555'
);

-- 3️⃣ Create table to track items in each approval (for audit and rollback)
CREATE TABLE restaurant_service.restaurant_approval_items
(
    id          UUID PRIMARY KEY,
    approval_id UUID           NOT NULL,
    product_id  UUID           NOT NULL,
    quantity    INTEGER        NOT NULL CHECK (quantity > 0),
    price       NUMERIC(10, 2) NOT NULL,
    created_at  TIMESTAMPTZ    NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_approval
        FOREIGN KEY (approval_id)
        REFERENCES restaurant_service.restaurant_approvals(id)
        ON DELETE CASCADE
);

-- 4️⃣ Create index for performance
CREATE INDEX IF NOT EXISTS idx_approval_items_approval_id
    ON restaurant_service.restaurant_approval_items(approval_id);

CREATE INDEX IF NOT EXISTS idx_approval_items_product_id
    ON restaurant_service.restaurant_approval_items(product_id);

-- 5️⃣ Add comment for documentation
COMMENT ON COLUMN restaurant_service.restaurant_products.stock IS
    'Current stock quantity available. Updated when orders are approved/rejected.';

COMMENT ON TABLE restaurant_service.restaurant_approval_items IS
    'Tracks individual items in each approval for audit trail and potential rollback.';
