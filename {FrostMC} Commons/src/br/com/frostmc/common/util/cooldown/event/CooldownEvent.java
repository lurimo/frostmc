package br.com.frostmc.common.util.cooldown.event;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import br.com.frostmc.common.util.cooldown.Cooldown;

@RequiredArgsConstructor
public abstract class CooldownEvent extends Event {

    @Getter
    @NonNull
    private Player player;

    @Getter
    @NonNull
    private Cooldown cooldown;
}
