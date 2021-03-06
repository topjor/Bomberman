package io.github.mdsimmo.bomberman.prizes;

import org.bukkit.entity.Player;

import io.github.mdsimmo.bomberman.messaging.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A payment that is an amount of experience points
 */
public final class XpPayment implements Payment {

    /**
     * Makes a payment from an amount of xp points
     * @param amount the amount of xp point the payment represents
     * @return an XpPayment of {@code amount} cp points
     */
    public static XpPayment of( int amount ) {
        return new XpPayment( amount );
    }

    private final int amount;

    private XpPayment( int amount ) {
        if ( amount < 0 )
            throw new IllegalArgumentException( "amount must be greater or eaqual to 0" );
        this.amount = amount;
    }

    /**
     * Gets the amount of xp points that this XpPayment represents
     * @return the amount of xp points
     */
    public int getAmount() {
        return amount;
    }

    private boolean ownedBy( Player player ) {
        return player.getTotalExperience() >= amount;
    }

    @Override
    public void giveTo( Player player ) {
        player.giveExp( amount );
    }

    @Override
    public boolean takeFrom( Player player ) {
        if ( !ownedBy( player ))
            return false;
        player.setTotalExperience( player.getTotalExperience() - amount );
        return true;
    }

    @Override
    public int hashCode() {
        return amount;
    }

    /**
     * An other object is only equal if it is another XpPayment and of an
     * equal amount
     * @param obj the object to test for equality
     * @return true if the object is equal
     */
    @Override
    public boolean equals( Object obj ) {
        if ( obj instanceof XpPayment )
            return ((XpPayment) obj).amount == this.amount;
        return false;
    }

    @Override
    public String toString() {
        return "XpPayment: " + amount;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put( "amount", amount );
        return map;
    }

    public static XpPayment deserialize( Map<String, Object> map ) {
        int amount = (Integer)map.get( "amount" );
        return XpPayment.of( amount );
    }

	@Override
	public String format( Message message, List<String> args ) {
		if ( args.size() > 0 ) {
			String arg = args.get( 0 ).toLowerCase();
			if ( arg.equals( "amount" ) ) {
				return Integer.toString( amount );
			}
			if ( arg.equals( "ptype" ) ) {
				return "xp";
			}
		}
		return amount + "xp";
	}
}
