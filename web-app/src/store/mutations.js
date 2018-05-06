import { UPDATE_MAIN_LOADING, UPDATE_MENU_INFO } from './constant'
export default {
  // 更新 store 中的加载状态
  [UPDATE_MAIN_LOADING] (state, { isLoading }) {
    state.mainLoading = isLoading
  },
  // 更新角色信息
  [UPDATE_MENU_INFO] (state, { menuInfo }) {
    state.menuInfo = menuInfo
  }
}
