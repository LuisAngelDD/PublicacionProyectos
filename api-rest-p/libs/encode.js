const bcrypt = require('bcryptjs')
const encode= async (data) => {
    const salt = await bcrypt.genSalt(10);
    return await bcrypt.hash(data, salt);
};
const decode= async (dataSend, dataStorage) => {
    return await bcrypt.compare(dataSend, dataStorage)
};
module.exports = {encode,decode}