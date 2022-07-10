const express = require('express');
const expRt = express.Router();
const ctrlFollows = require('../controles/follows');
const {checkToken} = require('../libs/tokens')

expRt.get('/follow/addRemove/:code',checkToken,ctrlFollows.addRemoveFollow);
expRt.get('/follow/myFollows/:status/:type/',checkToken,ctrlFollows.myFollows);
expRt.get('/follow/myFollows/:status/:type/:nombre',checkToken,ctrlFollows.myFollowsName);
expRt.get('/follow/if/:code',checkToken,ctrlFollows.ifFollow);
expRt.get('/follow/count/:code',ctrlFollows.getFollows)
expRt.get('/follow/get/:code',ctrlFollows.getSFollows)
/*expRt.get('/proyecto/', ctrlProyect.getAllProyects);
expRt.get('/proyecto/:proyectoId', ctrlProyect.getProyect);
expRt.put('/proyecto/:proyectoId', tk,ctrlProyect.updateProyect);
expRt.delete('/proyecto/:proyectoId', tk,ctrlProyect.deleteProyect);*/
module.exports = expRt;