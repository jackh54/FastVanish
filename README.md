# FastVanish

A high-performance vanish plugin for Minecraft 1.21+ with PlaceholderAPI integration.

## Features

- **High Performance**: Optimized for maximum performance and stability
- **PlaceholderAPI Integration**: Real player count and vanish status placeholders
- **Comprehensive Vanish System**: Complete vanish functionality with configurable restrictions
- **Persistence**: Vanish status is saved across server restarts
- **Permission System**: Granular permission control
- **No Tab Completion**: Clean command interface without tab completion clutter

## Commands

| Command | Aliases | Permission | Description |
|---------|---------|------------|-------------|
| `/vanish` | `/v`, `/hide`, `/show` | `fastvanish.vanish` | Toggle vanish status |
| `/vanishlist` | `/vl`, `/vanished` | `fastvanish.vanishlist` | List all vanished players |

## Permissions

| Permission | Default | Description |
|------------|---------|-------------|
| `fastvanish.vanish` | `op` | Allows players to vanish |
| `fastvanish.vanish.others` | `op` | Allows players to vanish other players |
| `fastvanish.vanishlist` | `op` | Allows players to see the vanish list |
| `fastvanish.bypass` | `op` | Bypass vanish restrictions |

## PlaceholderAPI

The plugin provides the following placeholders:

| Placeholder | Description |
|-------------|-------------|
| `%fastvanish_real_count%` | Real player count (excluding vanished players) |
| `%fastvanish_vanished%` | Vanish status text (configurable) |
| `%fastvanish_vanished_boolean%` | Boolean vanish status (true/false) |

## Configuration

### General Settings
```yaml
general:
  debug: false
  save-vanish-status: true
  show-vanish-messages: false
```

### Vanish Restrictions
```yaml
vanish:
  can-pickup-items: false
  can-push-entities: false
  can-be-pushed: false
  can-interact: false
  can-break-blocks: false
  can-place-blocks: false
  can-use-items: false
  can-drop-items: false
  can-take-damage: false
  can-deal-damage: false
  can-be-targeted: false
  can-be-seen: false
  can-see-vanished: true
  can-see-invisible: true
```

### Messages
```yaml
messages:
  prefix: "&8[&bFastVanish&8] &r"
  vanished: "&aYou are now vanished!"
  unvanished: "&cYou are no longer vanished!"
  vanished-other: "&aYou have vanished &e{player}&a!"
  unvanished-other: "&cYou have unvanished &e{player}&c!"
  already-vanished: "&cThat player is already vanished!"
  not-vanished: "&cThat player is not vanished!"
  no-permission: "&cYou don't have permission to do that!"
  player-not-found: "&cPlayer not found!"
  vanish-list: "&aVanished players: &e{players}"
  no-vanished-players: "&aNo players are currently vanished."
```

### PlaceholderAPI Settings
```yaml
placeholders:
  enabled: true
  real-player-count: "%fastvanish_real_count%"
  vanished-status: "%fastvanish_vanished%"
  vanished-text: "&7[Vanished]"
  not-vanished-text: ""
```

## Installation

1. Download the latest release
2. Place the JAR file in your server's `plugins` folder
3. Install PlaceholderAPI (required dependency)
4. Start your server
5. Configure the plugin in `plugins/FastVanish/config.yml`

## Building from Source

```bash
git clone https://github.com/pandadevv/FastVanish.git
cd FastVanish
mvn clean package
```

The compiled JAR will be in the `target` folder.

## Dependencies

- **Paper/Spigot 1.21+**
- **PlaceholderAPI** (required)

## Performance Features

- **Async Operations**: Vanish operations use async tasks when enabled
- **Caching**: Vanish status is cached for better performance
- **Concurrent Collections**: Thread-safe vanish player tracking
- **Optimized Events**: High-priority event handling for maximum compatibility

## Support

For support, please create an issue on GitHub or contact the author.

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Author

**pandadevv** - *Initial work* - [GitHub](https://github.com/pandadevv) 