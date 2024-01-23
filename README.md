# Leakus Deletus for Forge
Fixes a memory leak when using the RFTools Builder block and the Ore Excavation mod.

Tested on:
- Minecraft Forge version 1.20.1-47.2.17
- RF Tools Builder version 1.20-6.0.3
- Ore Excavation version 1.13.170

## ❓ What it fixes
The builder block uses Forge's fake player functionality to mimic a player when breaking blocks.
The FakePlayer object holds a dummy `net.minecraft.network.Connection` instance that is normally used for processing and queuing packets for normal players.
Normally, Ore Excavation sends data related to block breaking to a player's client to display an overlay, but in the case of a fake player, a packet is just added to the queue in the dummy connection instance.

The catch is that the queue never gets cleared by Minecraft or Forge, so any data not processed is staying forever in the connection's queue.

When using Leakus Deletus, it is first checked if the player owning that connection is real or a fake one. If it is fake, packets are not added to the connection's queue.

## 🧰 Building from sources
Leakus Deletus requires you to have at least JDK 17 installed. To build the project, run the following command in the project's root directory:
``./gradlew build``. If you don't want gradle to leave a daemon after building the project, append the ``--no-daemon`` flag.

## 🧑‍🤝‍🧑 Credits
Development environment configuration is copied from [Spottedleaf‘s Starlight mod](https://github.com/PaperMC/Starlight/tree/forge-scripts)
