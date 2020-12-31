# PitPresence
A simple Forge mod which allows you to have a custom Discord Rich Presence while playing the Hypixel Pit.

This is my first time using Forge so the code quality might not be ideal.

## Installation
1. Download the jar file from the releases page, or compile it yourself.
2. Put the jar file in your .minecraft/mods folder.
3. Start your game.
4. Join the Pit.

## Usage
- `/pitpresence details some gaming` to set the details.
- `/pitpresence state more gaming` to set the state.
- `/pitpresence large much gaming` to set the large image text.
- `/pitpresence small very much gaming` to set the small image text.
- Look at the Variables section to see what you can use to set your presence.
- `/pitpresence preview ${GOLD} gaming ${NAME}` to preview the text.

## Command Examples
- `/pitpresence details [${PRESTIGE}-${LEVEL}] ${NAME} | ${STATUS}`
- `/pitpresence state Streak: ${STREAK} | Bounty: ${BOUNTY}`
- `/pitpresence large ${GOLD} | ${NEEDEDXP} XP left`
- `/pitpresence small ${STATUS} | ${LOBBY} | ${DATE}`
- `/pitpresence preview [${PRESTIGE}-${LEVEL}] | ${STATUS}: ${STREAK}`

## Variables
All the data (currently) comes from the scoreboard.
Everything updates every 15 seconds.

You put these in the `/pitpresence` command.
- `${DATE}` the date, `12/31/2020`
- `${LOBBY}` current lobby, `mega32A`
- `${PRESTIGE}` current prestige, `XXXII`
- `${LEVEL}` current level, `32`
- `${NEEDEDXP}` needed XP for the next level, `1337`
- `${GOLD}` current amount of gold, `32,420g`
- `${STATUS}` current status, can be `Fighting`, `Idling`, `Event`, etc
- `${BOUNTY}` current bounty, `5000g`
- `${STREAK}` current killstreak, `1337.0`
- `${NAME}` account username, `Antonio32A`

## Credits
- https://github.com/jagrosh/
- https://sk1er.club/modcore
- https://github.com/DJtheRedstoner/