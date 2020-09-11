package me.devwckd.factions.entity.faction;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.devwckd.factions.entity.member.MemberMap;

/**
 * @author devwckd
 */

@Data
@AllArgsConstructor
public class Faction {

    private String name;
    private String tag;

    private final MemberMap memberMap = new MemberMap();

}
