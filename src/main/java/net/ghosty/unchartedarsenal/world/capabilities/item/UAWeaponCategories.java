package net.ghosty.unchartedarsenal.world.capabilities.item;

import yesman.epicfight.world.capabilities.item.WeaponCategory;

public enum UAWeaponCategories implements WeaponCategory {
    PHARAOH_CURSE, CALIBURN;

    final int id;

    UAWeaponCategories() {
        this.id = WeaponCategory.ENUM_MANAGER.assign(this);
    }

    @Override
    public int universalOrdinal() {
        return this.id;
    }
}
