const express = require('express');
const expRt = express.Router();
const ctrlStatus = require('../controles/status');
expRt.get('/status/',ctrlStatus.getAllStatus);
module.exports = expRt;