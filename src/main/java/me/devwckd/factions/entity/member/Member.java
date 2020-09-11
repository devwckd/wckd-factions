package me.devwckd.factions.entity.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.devwckd.factions.entity.faction.Faction;
import me.devwckd.factions.entity.faction.Role;
import me.devwckd.factions.entity.user.User;

/**
 * @author devwckd
 */

@Getter
@RequiredArgsConstructor
public class Member {
    private final Faction faction;
    private final User user;

    private Role role;
}
