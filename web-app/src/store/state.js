import Constant from '../plugin/auth-client/utils/constant'
export default {
  userInfo: JSON.parse(localStorage.getItem(Constant.user_info)),
  menuInfo: [],
  mainLoading: false
}
