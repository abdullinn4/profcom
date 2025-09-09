import {DocGeneratorUI} from "../../features"
import {useParams} from "react-router-dom";

interface DocGeneratorPageProps {
    type: "university" | "profcom";
}

export const DocGeneratorPage = ({ type }: DocGeneratorPageProps) => {
    const { id } = useParams();

    return (
        <DocGeneratorUI
            type={type}
            id={id}
        />
    );
};