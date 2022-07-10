const express = require('express');
const expRt = express.Router();
const ctrlLikes = require('../controles/likes');
const {checkToken} = require('../libs/tokens')

expRt.get('/like/addRemove/:code',checkToken,ctrlLikes.addRemoveLike);
expRt.get('/like/if/:code',checkToken,ctrlLikes.ifLike);
expRt.get('/like/count/:code',ctrlLikes.getLikes)
expRt.get('/like/get/:code',ctrlLikes.getSLikes)
/*expRt.get('/proyecto/', ctrlProyect.getAllProyects);
expRt.get('/proyecto/:proyectoId', ctrlProyect.getProyect);
expRt.put('/proyecto/:proyectoId', tk,ctrlProyect.updateProyect);
expRt.delete('/proyecto/:proyectoId', tk,ctrlProyect.deleteProyect);*/
module.exports = expRt;