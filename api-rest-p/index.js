const app = require('./app');
const {port,db} = require('./libs/config')
const {connect} = require('mongoose');
const roles = require('./libs/init');
connect(db).then(db => console.log('BD esta conectada'),{
    useNewUrlParser: true,
    useUnifiedTopology: true,
    useCreateIndex: true,
}).then(correct => roles()).catch(error => console.log(error));
app.listen(port);
console.log(`Servidor Rest corriendo en http://localhost:${port}`)
