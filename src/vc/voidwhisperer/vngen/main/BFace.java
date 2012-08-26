/*******************************************************************************
* Copyright (C) 2012 VoidWhisperer
*
* This work is licensed under the Creative Commons
* Attribution-NonCommercial-NoDerivs 3.0 Unported License. To view a copy of
* this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send
* a letter to Creative Commons, 171 Second Street, Suite 300, San Francisco,
* California, 94105, USA.
******************************************************************************/
package vc.voidwhisperer.vngen.main;

import org.bukkit.util.Vector;

/**
* Represents the face of a block
*/
public enum BFace {
    WEST(-1, 0, 0),
    NORTH(0, 0, -1),
    EAST(1, 0, 0),
    SOUTH(0, 0, 1),
    UP(0, 1, 0),
    DOWN(0, -1, 0),
    NORTH_EAST(NORTH, EAST),
    NORTH_WEST(NORTH, WEST),
    SOUTH_EAST(SOUTH, EAST),
    SOUTH_WEST(SOUTH, WEST),
    WEST_NORTH_WEST(WEST, NORTH_WEST),
    NORTH_NORTH_WEST(NORTH, NORTH_WEST),
    NORTH_NORTH_EAST(NORTH, NORTH_EAST),
    EAST_NORTH_EAST(EAST, NORTH_EAST),
    EAST_SOUTH_EAST(EAST, SOUTH_EAST),
    SOUTH_SOUTH_EAST(SOUTH, SOUTH_EAST),
    SOUTH_SOUTH_WEST(SOUTH, SOUTH_WEST),
    WEST_SOUTH_WEST(WEST, SOUTH_WEST),
    SELF(0, 0, 0);

    private final int modX;
    private final int modY;
    private final int modZ;

    private BFace(final int modX, final int modY, final int modZ) {
        this.modX = modX;
        this.modY = modY;
        this.modZ = modZ;
    }

    private BFace(final BFace face1, final BFace face2) {
        this.modX = face1.getModX() + face2.getModX();
        this.modY = face1.getModY() + face2.getModY();
        this.modZ = face1.getModZ() + face2.getModZ();
    }

    /**
* Get the amount of X-coordinates to modify to get the represented block
*
* @return Amount of X-coordinates to modify
*/
    public int getModX() {
        return modX;
    }

    /**
* Get the amount of Y-coordinates to modify to get the represented block
*
* @return Amount of Y-coordinates to modify
*/
    public int getModY() {
        return modY;
    }

    /**
* Get the amount of Z-coordinates to modify to get the represented block
*
* @return Amount of Z-coordinates to modify
*/
    public int getModZ() {
        return modZ;
    }

    /**
* @return A vector pointing out of this face.
*/
    public Vector toVector() {
        return new Vector(modX, modY, modZ);
    }

    /**
* @return The face opposite to this one.
*/
    public BFace getOppositeFace() {
        switch (this) {
        case NORTH:
            return BFace.SOUTH;

        case SOUTH:
            return BFace.NORTH;

        case EAST:
            return BFace.WEST;

        case WEST:
            return BFace.EAST;

        case UP:
            return BFace.DOWN;

        case DOWN:
            return BFace.UP;

        case NORTH_EAST:
            return BFace.SOUTH_WEST;

        case NORTH_WEST:
            return BFace.SOUTH_EAST;

        case SOUTH_EAST:
            return BFace.NORTH_WEST;

        case SOUTH_WEST:
            return BFace.NORTH_EAST;

        case WEST_NORTH_WEST:
            return BFace.EAST_SOUTH_EAST;

        case NORTH_NORTH_WEST:
            return BFace.SOUTH_SOUTH_EAST;

        case NORTH_NORTH_EAST:
            return BFace.SOUTH_SOUTH_WEST;

        case EAST_NORTH_EAST:
            return BFace.WEST_SOUTH_WEST;

        case EAST_SOUTH_EAST:
            return BFace.WEST_NORTH_WEST;

        case SOUTH_SOUTH_EAST:
            return BFace.NORTH_NORTH_WEST;

        case SOUTH_SOUTH_WEST:
            return BFace.NORTH_NORTH_EAST;

        case WEST_SOUTH_WEST:
            return BFace.EAST_NORTH_EAST;

        case SELF:
            return BFace.SELF;
        }

        return BFace.SELF;
    }
}