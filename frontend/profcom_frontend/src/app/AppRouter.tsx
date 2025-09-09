import {Navigate, Route, Routes} from "react-router-dom";
import {HomePage} from "../pages/home";
import {ProfcomTypePage, UniTypePage} from "../pages/chooseType";
import {UniversityFormPage} from "../pages/universityForm";
import {DocGeneratorPage} from "../pages/docGenerator";
import { ProfilePage } from "../pages/profile";
import {EditProfilePage} from "../pages/editProfile";
import {ProfUnionFormPage} from "../pages/profUnionForm";
import {BudgetCategoriesPage} from "../pages/budgetCategories";
import {ProfcomCategoriesPage} from "../pages/profcomCategories";

export const AppRouter = () => {
    return(
        <Routes>
            <Route path="/home" element={<HomePage/>}/>
            <Route path="/" element={<Navigate to="/home" replace/>}/>
            <Route path="/choose-type/university" element={<UniTypePage/>}/>
            <Route path="/choose-type/profcom" element={<ProfcomTypePage/>}/>
            <Route path="/generator_form/university_aid/:id" element={<UniversityFormPage/>}/>
            <Route path="/generator_form/prof-union_aid/:id" element={<ProfUnionFormPage/>}/>
            <Route path="/generator_form/university/:id" element={<DocGeneratorPage type="university"/>}/>
            <Route path="/generator_form/profcom/:id" element={<DocGeneratorPage type="profcom"/>}/>
            <Route path="/profile" element={<ProfilePage/>}/>
            <Route path="/profile/edit" element={<EditProfilePage/>}/>
            <Route path="/categories/budget" element={<BudgetCategoriesPage />} />
            <Route path="/categories/profcom" element={<ProfcomCategoriesPage />} />
        </Routes>
    )
}