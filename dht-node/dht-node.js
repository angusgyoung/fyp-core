const DHT = require("bittorrent-dht");
const express = require("express");

const bootstrapListenPort = process.env.NODE_PORT || 6881;
const serverListenPort = process.env.API_PORT || 3000;

const server = express();
server.use(express.json());

// Set up our bootstrap node. Other nodes will initialise their routing tables from this node
const nodeConfig = {
    // override bootstrap to prevent our node from
    // trying to connect to the torrent network
    bootstrap: process.env.BOOTSTRAP_NODE_ADDR
        ? [process.env.BOOTSTRAP_NODE_ADDR]
        : false,
    maxAge: 1000
};

let dhtNode = new DHT(nodeConfig);

dhtNode.listen(bootstrapListenPort, () => {
    console.info("DHT: Starting DHT node on port:", bootstrapListenPort);
    console.debug("DHT: Initialised node using config:", nodeConfig);
});

// Register DHT event listeners
dhtNode.on("ready", () =>
    console.info("DHT: Node is ready to accept connections")
);
dhtNode.on("node", node => {
    dhtNode.addNode(node);
    console.debug(
        "DHT: A new node has been added to the routing table:",
        `${node.host}:${node.port}`
    );
    console.debug(dhtNode.toJSON());
});
dhtNode.on("error", error =>
    console.error("DHT: A fatal error has occurred:", error)
);
dhtNode.on("warning", warning =>
    console.warn("DHT: A warning has been issued:", warning)
);

// Retrieve a DHT entry with the given key
server.get("/lookup/:key", (req, res) => {
    let key = req.params.key;

    dhtNode.get(key, (error, result) => {
        if (result) {
            let data = {
                nodeId: result.id.toString("hex"),
                value: result.v.toString()
            };
            res.status(200).json({
                error: error ? error.message : undefined,
                data: data
            });
        } else {
            res.sendStatus(404);
        }
    });
});

// Insert some data into the DHT and provide a key
server.put("/insert", (req, res) => {
    let data = req.body.data;
    let buff = new Buffer.from(data);

    dhtNode.put({ v: buff }, (error, hash, n) => {
        let key = hash.toString("hex");
        res.status(200).json({
            error: error ? error.message : undefined,
            key: key,
            nodes: n
        });
    });
});

server.listen(serverListenPort, () =>
    console.info("DHT: API listening on port", serverListenPort)
);
