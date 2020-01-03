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

dhtNode.listen(listenPort, () => console.log(""));

// Register event listeners
dhtNode.on("ready", () =>
  console.info("DHT node is ready to accept connections")
);
dhtNode.on("listening", () =>
  console.debug("DHT node is listening for connections", nodeConfig)
);
dhtNode.on("node", node =>
  console.debug("A new node has been added to the DHT", node)
);
dhtNode.on("error", error =>
  console.error("A fatal error has occurred in the DHT", error)
);
dhtNode.on("warning", warning =>
  console.warn("A warning has been issues in the DHT", warning)
);
