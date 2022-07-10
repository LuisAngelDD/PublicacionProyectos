const express = require('express');
const expRt = express.Router();
const ctrlType = require('../controles/type');

expRt.get('/type/',ctrlType.getAllType);
module.exports = expRt;