package me.devwckd.factions.entity.faction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import me.devwckd.factions.entity.member.MemberMap;

import java.util.UUID;

/**
 * @author devwckd
 */

@Data
@AllArgsConstructor
public class Faction {

    private final UUID id;
    private final MemberMap memberMap = new MemberMap();

    private String name;
    private String tag;

}
