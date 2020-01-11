const assert = require("assert");
const DHT = require("bittorrent-dht");

const listenPort = process.env.NODE_PORT || 6969;

// Set up our bootstrap node. Other nodes will initialise their routing tables from this node
const nodeConfig = {
  // override bootstrap to prevent our node from
  // trying to connect to the torrent network
  bootstrap: process.env.BOOTSTRAP_NODE_ADDR
    ? [process.env.BOOTSTRAP_NODE_ADDR]
    : []
};

let dhtNode = new DHT(nodeConfig);

dhtNode.listen(listenPort, () => {
  console.info("Starting DHT node on port", listenPort);
  console.debug("Initialised node using config:", nodeConfig);
});

// Register event listeners
dhtNode.on("ready", () =>
  console.info("DHT node is ready to accept connections")
);
dhtNode.on("node", node =>
  console.debug(
    "A new node has been added to the DHT:",
    `${node.host}:${node.port}`
  )
);
dhtNode.on("error", error =>
  console.error("A fatal error has occurred in the DHT", error)
);
dhtNode.on("warning", warning =>
  console.warn("A warning has been issued by the DHT", warning)
);
